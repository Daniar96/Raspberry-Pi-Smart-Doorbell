#!/bin/sh
cd /home/bogdan/Rpi_server/rfid ;
sudo python3 /home/bogdan/Rpi_server/rfid/RFID.py &
cd /home/bogdan/Rpi_server/smoke;
sudo python3 /home/bogdan/Rpi_server/smoke/mq.py &
# cd /home/bogdan/Rpi_server;
# sudo python3 /home/bogdan/Rpi_server/app.py &

