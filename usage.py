import psutil
import datetime;

while True:
    ts = datetime.datetime.now()
    print ('TIME ', ts , ' CPU ', psutil.cpu_percent(1), 'MEMORY', psutil.virtual_memory().percent)
