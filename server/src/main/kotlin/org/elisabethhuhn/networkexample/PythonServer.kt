package org.elisabethhuhn.networkexample
//
////
//from flask import Flask, request, jsonify
//from datetime import datetime
//from enum import Enum
//from typing import List
//
//app = Flask(__name__)
//
//# Define valid severity levels
//class Severity(str, Enum):
//    DEBUG = "DEBUG"
//INFO = "INFO"
//WARN = "WARN"
//ERROR = "ERROR"
//
//@app.route('/log', methods=['POST'])
//def log():
//# Get the JSON data from the request
//data = request.get_json()
//
//# Validate that we received a list
//if not isinstance(data, list):
//return jsonify({"error": "Request body must be an array"}), 400
//
//# Validate each log entry
//for entry in data:
//# Check if entry is a dict and has required fields
//if not isinstance(entry, dict):
//return jsonify({"error": "Each log entry must be an object"}), 400
//
//# Check for required fields
//required_fields = {'message', 'timestamp', 'severity'}
//missing_fields = required_fields - set(entry.keys())
//if missing_fields:
//return jsonify({
//    "error": f"Missing required fields: {', '.join(missing_fields)}"
//}), 400
//
//# Validate message is a string
//if not isinstance(entry['message'], str):
//return jsonify({"error": "Message must be a string"}), 400
//
//# Validate severity
//if entry['severity'] not in Severity.__members__:
//return jsonify({
//    "error": f"Invalid severity level. Must be one of: {', '.join(Severity.__members__.keys())}"
//}), 400
//
//# Validate timestamp
//try:
//datetime.fromisoformat(entry['timestamp'].replace('Z', '+00:00'))
//except (ValueError, AttributeError):
//return jsonify({"error": "Invalid timestamp format"}), 400
//
//# If we get here, everything is valid
//return jsonify({"status": "success"}), 200
//
//if __name__ == '__main__':
//app.run(host='0.0.0.0', port=5000)