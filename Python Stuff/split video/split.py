import os,time
first=open('1.mp4','rb').read()
second=open('2.mp4','wb')
for i in range(0,os.path.getsize('1.mp4'),5242880):
	second.write(first[i:i+5242880])
	print("done")
	time.sleep(5)
second.close()
print("finally done")
	