from flask import Flask

app = Flask(__name__)

@app.route('/')
def page():
    return 'yo'

@app.route('/bogdi')
def yo():
    return 'bruh'

if __name__ == '__main__':
    app.run()
