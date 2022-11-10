import adafruit_dht
from board import *
import time
import requests
import request

SENSOR_PIN = D6

dht22 = adafruit_dht.DHT22(SENSOR_PIN, use_pulseio=False)

while True:
    temperature = dht22.temperature
    humidity = dht22.humidity

    print(f"Humidity= {humidity:.2f}")
    print(f"Temperature= {temperature:.2f}°C")

    headers = {
    "Content-Type": "application/json"
    }

    URL = request.url + "setTemp"

    t = f"{temperature:.1f}"
    h = f"{humidity:.1f}"

    print(type(t))

    data = {"temp" : t, "humidity" : h}

    r = requests.post(url = URL, data = data)

    try:
        r = requests.post(url = URL, headers=headers, json = data, cookies=request.cookie, verify = False)
    except Exception as e:
        print(e)
    response = r.status_code
    print(response)
    time.sleep(20)