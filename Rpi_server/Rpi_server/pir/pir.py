import datetime
from picamera import PiCamera
from gpiozero import MotionSensor
import time
import base64
import json
import requests
import sys


pir = MotionSensor("GPIO16")
camera = PiCamera()

def getFileName():
	return datetime.datetime.now().strftime("%Y-%m-%d_%H.%M.%S.jpg")

while True:
    url = 'http://192.168.92.24:8080/Server_war/api/RPI/image'
    filename = getFileName()
    
    pir.wait_for_motion()
    
    print("pidar detected!")
    
    camera.capture(filename)

    with open(filename, "rb") as f:
        imbytes = f.read()        
    b64 = base64.b64encode(imbytes).decode("utf8")

    headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}

    payload = {"name" : filename, "encode" : b64}
    print(sys.getsizeof(payload))
    response = requests.post(url = url, headers = headers, json = payload, verify = False)

    time.sleep(4)