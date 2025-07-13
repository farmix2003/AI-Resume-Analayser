from flask import Flask, request, jsonify
from analayzer import analyze_resume

app = Flask(__name__)

@app.route('/analyze', methods=['POST'])
def analyze():
    data = request.get_json()
    resume = data.get('resume', '')
    jd = data.get('jd', '')

    result = analyze_resume(resume, jd)
    return jsonify(result)

if __name__ == '__main__':
    app.run(port=5000)
