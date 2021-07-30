#!/bin/bash

curl -sL https://github.com/shyiko/jabba/raw/master/install.sh | bash && . ~/.jabba/jabba.sh

jabba install openjdk@1.11.0-2
jabba alias default openjdk@1.11.0-2
jabba use default

echo "*** make sure to have 'jabba use default' in your .bashrc/.zshrc ***"