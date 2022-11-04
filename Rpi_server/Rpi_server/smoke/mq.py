import RPi.GPIO as GPIO
import time
import requests

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

            URL = "http://192.168.137.1:8080/Server_war/api/RPI/smoke"
            data = {"alert":1}
            r = requests.post(url = URL, data = data)
            try:
                r = requests.post(url = URL, headers=headers, json = data, verify = False)
            except Exception as e:
                print(e)
            response = r.status_code
            print(response)
            time.sleep(1)
        elif alert == 1:
            alert = 0
            URL = "http://192.168.137.1:8080/Server_war/api/RPI/smoke"
            data = {"alert":0}
            try:
                r = requests.post(url = URL, headers=headers, json = data, verify = False)
            except Exception as e:
                print(e) 
            response = r.status_code
            print(response)
except:
    GPIO.cleanup()
finally:
    GPIO.cleanup()
