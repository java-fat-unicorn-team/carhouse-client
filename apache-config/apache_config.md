# Configure Apache Server
### 1. Install Apache
 *skip this point if you already have apache server on your computer
 ```
 $ sudo apt update
 $ sudo apt install apache2
```
### 2. Configuring firewall
run this command to allow Apache on your UFW firewall.
 ```
 $ sudo ufw allow 'Apache'
```
now check status of your apache server
```
 $ systemctl status apache2
```
### 3. Add modules to the apache server
```
 $ sudo a2enmod proxy
 $ sudo a2enmod proxy_http
```
### 4. Add proxy to the apache server
go to the config file
```
 $ sudo nano /etc/apache2/sites-available/000-default.conf
```
and put text below to the config file
```
<VirtualHost *:80>
    ServerAdmin your@email  
    DocumentRoot /var/www/carhouse
    
    ProxyPreserveHost On

    ProxyPass /carhouse http://127.0.0.1:8099/carhouse/
    ProxyPassReverse /carhouse http://127.0.0.1:8099/carhouse/

    ErrorLog ${APACHE_LOG_DIR}/error.log
    CustomLog ${APACHE_LOG_DIR}/access.log combined
</VirtualHost>
```
### 5. Create folder to store images
```
 $ cd /var/www
 $ sudo mkdir carhouse
 $ sudo chmod 777 carhouse
 $ setfacl -d -m o::rx carhouse
```
### 6. Restart server
```
 $ systemctl restart apache2
```