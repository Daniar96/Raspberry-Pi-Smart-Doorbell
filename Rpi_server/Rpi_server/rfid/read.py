import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522
import requests

reader = SimpleMFRC522()

try:
        id, text = reader.read()
        f = open("demo.txt", "a")
        f.write(str(id) + "\n")
        f.close()


finally:
        GPIO.cleanup()