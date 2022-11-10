import json
import request
import RPi.GPIO as GPIO
import time
import requests
from mfrc522 import SimpleMFRC522

GPIO.setmode(GPIO.BCM)
GPIO.setup(23, GPIO.OUT)

reader = SimpleMFRC522()

try:
    while True:
        id, text = reader.read()
        f = open("rfid/demo.txt", "a")
        f.write("\n" + str(id) + "\n")
        print("Read success\n")
        f.close()
        
        headers = {
        "Content-Type": "application/json"
        }
        URL = request.url + "authorize"
        st = str(id)
        data = {"tagID":st}
        try:
            r = requests.post(url = URL, headers=headers, json = data, cookies=request.cookie, verify = False)
        except Exception as e:
            print(e)

        response = r.status_code
        print(response)


        if response == 200:
            GPIO.output(23,GPIO.HIGH) # LED is switched on
            time.sleep(0.1)  # Wait mode for 4 seconds
            GPIO.output (23,GPIO.LOW) # LED is switched off
            time.sleep (0.25)
            GPIO.output(23,GPIO.HIGH) # LED is switched on
            time.sleep(0.1)  # Wait mode for 4 seconds
            GPIO.output (23,GPIO.LOW) # LED is switched off
            time.sleep (0.25)
        elif response == 401:
            GPIO.output(23,GPIO.HIGH) # LED is switched on
            time.sleep(0.1)  # Wait mode for 4 seconds
            GPIO.output (23,GPIO.LOW) # LED is switched off
            time.sleep (0.25)

except:
    GPIO.cleanup()
finally:
    GPIO.cleanup()