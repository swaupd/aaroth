
import json
from pymongo import MongoClient

# MongoDB connection
client = MongoClient('localhost', 27017)
db = client['aarothdb']
collection = db['customers']

def import_report(json_file):
    with open(json_file, 'r') as f:
        data = json.load(f)
        
    for report in data:
        uuid = report["UUID"]
        user = collection.find_one({"UUID": uuid})
        if user:
            collection.update_one(
                {"UUID": uuid},
                {"$push": {"Reports": report}}
            )
            print(f"Report added to customer with UUID: {uuid}")
        else:
            print(f"No customer found with UUID: {uuid}")

# Example usage:
if __name__ == "__main__":
    import_report("in.json")
