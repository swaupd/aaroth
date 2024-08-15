import json
from pymongo import MongoClient
from reportlab.lib import colors
from reportlab.lib.pagesizes import letter
from reportlab.platypus import SimpleDocTemplate, Table, TableStyle
from reportlab.lib.styles import getSampleStyleSheet
from io import BytesIO
from flask import Flask, request, send_file

app = Flask(__name__)

def fetch_customer_data(phone_number):
    # Connect to MongoDB
    client = MongoClient('mongodb://Aaroth:aaroth123@docdb-2024-08-08-15-08-46.cjk4mgg8gxwz.ap-south-1.docdb.amazonaws.com:27017/?tls=true&tlsCAFile=global-bundle.pem&retryWrites=false')
    db = client['aarothdb']
    collection = db['customers']

    # Fetch the document with the given phone number
    document = collection.find_one({'Phone_Number': phone_number})
    return document

def create_pdf(customer_data):
    buffer = BytesIO()
    doc = SimpleDocTemplate(buffer, pagesize=letter)
    elements = []
    styles = getSampleStyleSheet()

    # Add customer details
    customer_details = [
        ['Name', customer_data.get('Name', 'N/A')],
        ['Age', str(customer_data.get('DOB', 'N/A'))],
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
    buffer.seek(0)
    return buffer

@app.route('/generate_report', methods=['POST'])
def generate_report():
    data = request.get_json()
    phone_number = data.get('phoneNumber')
    customer_data = fetch_customer_data(phone_number)
    if customer_data:
        pdf_buffer = create_pdf(customer_data)
        return send_file(pdf_buffer, as_attachment=True, download_name=f"{phone_number}_report.pdf", mimetype='application/pdf')
    else:
        return jsonify({"message": "No report found for the given phone number."}), 404

if __name__ == "__main__":
    app.run(debug=True)
