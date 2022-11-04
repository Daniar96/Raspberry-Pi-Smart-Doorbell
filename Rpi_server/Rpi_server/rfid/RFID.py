import json
from urllib import request
import RPi.GPIO as GPIO
import time
import requests
from mfrc522 import SimpleMFRC522

GPIO.setmode(GPIO.BCM)
GPIO.setup(23, GPIO.OUT)
GPIO.output(23, False)
reader = SimpleMFRC522()

try:
    while True:
        id, text = reader.read()
        f = open("demo.txt", "a")
        f.write("\n" + str(id) + "\n")
        print("Read success\n")
        f.close()
        
        headers = {
        "Content-Type": "application/json"
        }
        URL = "http://192.168.137.1:8080/Server_war/api/RPI/authorize"
        st = str(id)
        data = {"tagID":st}
        try:
            r = requests.post(url = URL, headers=headers, json = data, verify = False)
        except Exception as e:
            print(e)

        response = r.status_code
        print(response)

        if response == 200:
            GPIO.output(23, True)
            time.sleep(0.25)
            GPIO.output(23, False)
            time.sleep(0.25)
            GPIO.output(23, True)
            time.sleep(0.25)
            GPIO.output(23, False)
        elif response == 401:
            GPIO.output(23, True)
            time.sleep(0.25)
            GPIO.output(23, False)
            time.sleep(0.25)

except:
    GPIO.cleanup()
finally:
    GPIO.cleanup()