import torch
import urllib.parse
from transformers import AutoTokenizer, AutoModelForSequenceClassification
from flask import Flask, request, jsonify, make_response, abort
from flask_cors import CORS

# Create the Python server with flask and enable CORS
app = Flask(__name__)
CORS(app, resources = {r"/sarcasm": {
    "origins": "http://localhost:3000",
    "methods": ['GET']
}})

# Function for checking if a comment is sarcastic
@app.route("/sarcasm", methods = ['GET'])
def returnSarcasticState():

    # Get encoded comment in the URL and decode it
    encodedComment = request.args.get('comment')
    decodedComment = urllib.parse.unquote(encodedComment)

    # Take the model and the tokenizer of the fine-tuned sarcasm database
    model = AutoModelForSequenceClassification.from_pretrained("./saved-fifth-pretrain")
    tokenizer = AutoTokenizer.from_pretrained("./saved-fifth-pretrain")

    # Tokenize the comment
    tokenized_comment = tokenizer(decodedComment, return_tensors="pt", truncation=True, padding=True, max_length=512)

    # Turn the model to evaluation mode and generate the result of the tokenized comment
    model.eval()
    with torch.no_grad():
        results = model(**tokenized_comment)

    # Generate the prediction of the comment
    resulted_logits = results.logits
    probability = torch.nn.functional.softmax(resulted_logits, dim=-1)
    predicted_class = torch.argmax(probability, dim=-1).numpy()

    if predicted_class == 0:
        response = make_response(jsonify({"message": "The message is not sarcastic"}), 200)
    else:
        if probability.tolist()[0][1] < 0.95:
            response = make_response(jsonify({"message": "The message has an acceptable level of sarcasm"}), 200)
        else:
            response = make_response(jsonify({"message": "Cannot post a sarcastic comment! Please write something genuine."}), 403)
    return response

if __name__ == "__main__":
    app.run(debug=True)