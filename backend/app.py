from flask import Flask, request, jsonify, send_file
from flask_cors import CORS
from pymongo import MongoClient
import io
from reportlab.lib.pagesizes import letter
from reportlab.platypus import SimpleDocTemplate, Table, TableStyle
from reportlab.lib.styles import getSampleStyleSheet
from reportlab.lib import colors

app = Flask(__name__)
CORS(app)

# MongoDB connection
client = MongoClient('mongodb://localhost:27017/')
db = client['aarothdb']
customers_collection = db['customers']

# Function to generate next UUID
def generate_next_uuid():
    last_customer = customers_collection.find().sort('UUID', -1).limit(1)
    last_customer = list(last_customer)  # Convert cursor to list
    if len(last_customer) == 0:
        return '0000000000'
    last_uuid = last_customer[0]['UUID']
    next_uuid = increment_uuid(last_uuid)
    return next_uuid

def increment_uuid(uuid):
    uuid_chars = list(uuid)
    for i in range(len(uuid_chars) - 1, -1, -1):
        if uuid_chars[i] == 'Z':
            uuid_chars[i] = '0'
        else:
            uuid_chars[i] = chr(ord(uuid_chars[i]) + 1)
            break
    return ''.join(uuid_chars)

@app.route('/register', methods=['POST'])
def register_customer():
    data = request.json
    phone_number = data.get('phoneNumber')

    # Check if the phone number already exists in the database
    if customers_collection.find_one({'Phone_Number': phone_number}):
        return jsonify({"message": "Phone number already associated with a customer"}), 400

    new_customer = {
        'UUID': generate_next_uuid(),
        'Phone_Number': phone_number,
        'Name': data.get('name'),
        'DOB': data.get('dob'),
        'Gender': data.get('gender'),
        'Diet': data.get('diet'),
        'Reports': []
    }

    customers_collection.insert_one(new_customer)
    return jsonify({"message": "Customer registered successfully"})

def create_pdf(customer_data):
    pdf_buffer = io.BytesIO()
    doc = SimpleDocTemplate(pdf_buffer, pagesize=letter)
    elements = []
    styles = getSampleStyleSheet()

    # Add customer details
    customer_details = [
        ['Customer'],
        ['Name', customer_data.get('Name', 'N/A')],
        ['DOB', str(customer_data.get('DOB', 'N/A'))],
        ['Gender', customer_data.get('Gender', 'N/A')],
    ]
    details_table = Table(customer_details)
    details_table.setStyle(TableStyle([
        ('BACKGROUND', (0, 0), (-1, 0), colors.grey),
        ('TEXTCOLOR', (0, 0), (-1, 0), colors.whitesmoke),
        ('ALIGN', (0, 0), (-1, -1), 'CENTER'),
        ('FONTNAME', (0, 0), (-1, 0), 'Helvetica-Bold'),
        ('FONTSIZE', (0, 0), (-1, 0), 12),
        ('BOTTOMPADDING', (0, 0), (-1, 0), 12),
        ('BACKGROUND', (0, 1), (-1, -1), colors.beige),
        ('TEXTCOLOR', (0, 1), (-1, -1), colors.black),
        ('ALIGN', (0, 0), (-1, -1), 'LEFT'),
        ('FONTNAME', (0, 1), (-1, -1), 'Helvetica'),
        ('FONTSIZE', (0, 1), (-1, -1), 10),
        ('TOPPADDING', (0, 1), (-1, -1), 6),
        ('BOTTOMPADDING', (0, 1), (-1, -1), 6),
        ('GRID', (0, 0), (-1, -1), 1, colors.black)
    ]))
    elements.append(details_table)
    
    # Add report data
    def flatten_dict(d, parent_key=''):
        items = []
        for k, v in d.items():
            new_key = f"{parent_key}.{k}" if parent_key else k
            if isinstance(v, dict):
                items.extend(flatten_dict(v, new_key))
            else:
                items.append((new_key, str(v)))
        return items

    flat_data = []
    if customer_data and customer_data.get('Reports'):
        latest_report = customer_data['Reports'][-1]
        flat_data = flatten_dict(latest_report)

    table_data = [['Measurement', 'Value']]
    table_data.extend(flat_data)

    report_table = Table(table_data)
    report_table.setStyle(TableStyle([
        ('BACKGROUND', (0, 0), (-1, 0), colors.grey),
        ('TEXTCOLOR', (0, 0), (-1, 0), colors.whitesmoke),
        ('ALIGN', (0, 0), (-1, -1), 'CENTER'),
        ('FONTNAME', (0, 0), (-1, 0), 'Helvetica-Bold'),
        ('FONTSIZE', (0, 0), (-1, 0), 12),
        ('BOTTOMPADDING', (0, 0), (-1, 0), 12),
        ('BACKGROUND', (0, 1), (-1, -1), colors.beige),
        ('TEXTCOLOR', (0, 1), (-1, -1), colors.black),
        ('ALIGN', (0, 0), (-1, -1), 'LEFT'),
        ('FONTNAME', (0, 1), (-1, -1), 'Helvetica'),
        ('FONTSIZE', (0, 1), (-1, -1), 10),
        ('TOPPADDING', (0, 1), (-1, -1), 6),
        ('BOTTOMPADDING', (0, 1), (-1, -1), 6),
        ('GRID', (0, 0), (-1, -1), 1, colors.black)
    ]))
    elements.append(report_table)
    
    doc.build(elements)
    pdf_buffer.seek(0)
    return pdf_buffer

@app.route('/generate_report', methods=['POST'])
def generate_report():
    data = request.json
    phone_number = data.get('phoneNumber')

    customer_data = customers_collection.find_one({'Phone_Number': phone_number})

    if not customer_data:
        return jsonify({"message": "No customer found with the given phone number"}), 404

    pdf_buffer = create_pdf(customer_data)
    return send_file(pdf_buffer, as_attachment=True, download_name='customer_report.pdf', mimetype='application/pdf')
@app.route('/update_customer', methods=['POST'])
def update_customer():
    data = request.json
    uuid = data.get('uuid')
    name = data.get('name')
    phone_number = data.get('phoneNumber')
    diet = data.get('diet')
    if not uuid:
        return jsonify({"message": "UUID is required"}), 400

    # Check if customer exists
    customer = customers_collection.find_one({'UUID': uuid})
    if not customer:
        return jsonify({"message": "No customer found with the given UUID"}), 404

    # Prepare update fields
    update_fields = {}
    if name:
        update_fields['Name'] = name
    if phone_number:
        # Check if the new phone number already exists
        if customers_collection.find_one({'Phone_Number': phone_number}):
            return jsonify({"message": "Phone number already associated with another customer"}), 400
        update_fields['Phone_Number'] = phone_number
    if diet and diet != "No Change":
        update_fields['Diet'] = diet

    # Update customer data
    if update_fields:
        result = customers_collection.update_one({'UUID': uuid}, {'$set': update_fields})

        if result.matched_count == 0:
            return jsonify({"message": "No customer found with the given UUID"}), 404

        if result.modified_count == 0:
            return jsonify({"message": "No fields were updated"}), 400

        return jsonify({"message": "Customer data updated successfully"})
    else:
        return jsonify({"message": "No fields to update"}), 400
if __name__ == '__main__':
    app.run(debug=True)
