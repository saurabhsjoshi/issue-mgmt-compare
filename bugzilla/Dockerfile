FROM --platform=amd64 ubuntu/apache2:2.4-22.04_beta

RUN apt-get update && apt-get install -y \
    git nano apache2 build-essential mariadb-server \
    libcgi-pm-perl libdigest-sha-perl libtimedate-perl \
    libdatetime-perl libdatetime-timezone-perl \
    libdbi-perl libdbix-connector-perl supervisor \
    libtemplate-perl libemail-address-perl \
    libemail-sender-perl libemail-mime-perl \
    liburi-perl liblist-moreutils-perl \
    libmath-random-isaac-perl libjson-xs-perl \
    libgd-perl libchart-perl libtemplate-plugin-gd-perl \
    libgd-text-perl libgd-graph-perl libmime-tools-perl \
    libwww-perl libxml-twig-perl libnet-ldap-perl \
    libauthen-sasl-perl libnet-smtp-ssl-perl \
    libauthen-radius-perl libsoap-lite-perl \
    libxmlrpc-lite-perl libjson-rpc-perl \
    libtest-taint-perl libhtml-parser-perl \
    libhtml-scrubber-perl libencode-perl libencode-detect-perl \
    libemail-reply-perl libhtml-formattext-withlinks-perl \
    libtheschwartz-perl libdaemon-generic-perl libapache2-mod-perl2 \
    libapache2-mod-perl2-dev libfile-mimeinfo-perl libio-stringy-perl \
    libcache-memcached-perl libfile-copy-recursive-perl libfile-which-perl \
    libdbd-mysql-perl perlmagick lynx graphviz python3-sphinx rst2pdf \
    && rm -rf /var/lib/apt/lists/*

COPY --chown=root:root files-to-copy /

RUN service mariadb start \
    && mysql -u root -e 'CREATE DATABASE IF NOT EXISTS bugs CHARACTER SET = "utf8"' \
    && mysql -u root -e 'GRANT ALL PRIVILEGES ON bugs.* TO bugs@localhost IDENTIFIED BY "admin"' \
    && a2ensite bugzilla \
    && a2enmod cgi headers expires rewrite \
    && mkdir -p /var/www/webapps \
    && cd /var/www/webapps \
    && git clone --depth 1 --branch release-5.0-stable https://github.com/bugzilla/bugzilla bugzilla \
    && cd /var/www/webapps/bugzilla \
    && ./checksetup.pl /checksetup_answers.txt \
    && ./checksetup.pl /checksetup_answers.txt \
    && service mariadb stop

CMD ["/usr/bin/supervisord", "-c", "/etc/supervisord.conf"]
