import json
from pymongo import MongoClient

# MongoDB connection
client = MongoClient('mongodb://Aaroth:aaroth123@docdb-2024-08-08-15-08-46.cjk4mgg8gxwz.ap-south-1.docdb.amazonaws.com:27017/?tls=true&tlsCAFile=global-bundle.pem&retryWrites=false')
db = client['aarothdb']
collection = db['customers']

def import_report(data):
    for report in data:
        uuid = report.get("UUID")
        if uuid:
            user = collection.find_one({"UUID": uuid})
            if user:
                collection.update_one(
                    {"UUID": uuid},
                    {"$push": {"Reports": report}}
                )
                print(f"Report added to customer with UUID: {uuid}")
            else:
                print(f"No customer found with UUID: {uuid}")
        else:
            print("Report missing UUID")

