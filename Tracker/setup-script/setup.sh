sudo apt-get install postgresql
if psql -lqt | cut -d \| -f 1 | grep -w mydb; then
   	psql -d mydb -f db.sql
else
   	psql postgres -c "create database mydb"
	psql -d mydb -f db.sql
fi

sudo apt-get install npm
tar -xvf tracker.tar.gz
sudo chown -R root:root Tracker
cd Tracker
sudo npm -g install node
sudo npm -g install express
echo "Setup Complete. goto Tracker 'cd Tracker' and Run 'nodejs app'"
