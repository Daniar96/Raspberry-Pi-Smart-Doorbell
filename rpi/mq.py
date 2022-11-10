import RPi.GPIO as GPIO
import time
import requests
import request

pin = 4
GPIO.setmode(GPIO.BCM)
GPIO.setup(pin, GPIO.IN)
alert = 0

try:
    while True:
        if GPIO.input(pin) == 0:
            print("Smoke detected")
            alert = 1

            headers = {
            "Content-Type": "application/json"
            }

            URL = request.url + "smoke"
            data = {"alert":1}
            try:
                r = requests.post(url = URL, headers=headers, json = data, cookies=request.cookie, verify = False)
            except Exception as e:
                print(e)
            response = r.status_code
            print(response)
            time.sleep(1)
        elif alert == 1:
            alert = 0
            URL = request.url + "smoke"
            data = {"alert":0}
            try:
                r = requests.post(url = URL, headers=headers, json = data, cookies=request.cookie, verify = False)
            except Exception as e:
                print(e) 
            response = r.status_code
            print(response)
except:
    GPIO.cleanup()
finally:
    GPIO.cleanup()
