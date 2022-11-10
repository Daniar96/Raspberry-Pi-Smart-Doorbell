import datetime
from picamera import PiCamera
from gpiozero import MotionSensor
import time
import base64
import json
import requests
import request
import sys

pir = MotionSensor("GPIO16")
camera = PiCamera()

def getFileName():
	return datetime.datetime.now().strftime("%Y-%m-%d_%H.%M.%S.jpg")

while True:
    URL = request.url + "image"
    filename = getFileName()
    
    pir.wait_for_motion()
    
    print("movement detected!")
    
    camera.capture("pir/" + filename)

    with open("pir/" + filename, "rb") as f:
        imbytes = f.read()        
    b64 = base64.b64encode(imbytes).decode("utf8")

    headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}

    payload = {"name" : filename, "encode" : b64}
    print(sys.getsizeof(payload))
    response = requests.post(url = URL, headers = headers, json = payload, cookies=request.cookie, verify = False)

    time.sleep(4)