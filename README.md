# PriceChecker 

PriceCheckerBy monitors price changes for products on Belarusian online stores.

Functions:

- building price changes chart
- notifying the users of price reduction

## Supported online stores

![supported online stores](../media/images/stores.png?raw=true)

- Wildberries
- OZ
- 21vek
- Lamoda

## Clients

### chrome extension 

supported by Chrome, Opera, Yandex Browser

![extension](../media/images/extension/default.png?raw=true)

### website

![website](../media/images/website/start.png?raw=true)
![website with subscription](../media/images/website/with_subscription.png?raw=true)

## How to run

### Web service

#### Requirements

- Docker
- docker-compose

Open server/.env file and check of ```CHROMEDRIVER_DOWNLOAD_LINK``` and ```CHROME_DOWNLOAD_LINK``` properties are still relevant. You can find relevant links on [Chromedriver](https://chromedriver.storage.googleapis.com/index.html) and [Chrome](http://170.210.201.179/linux/chrome/deb/pool/main/g/google-chrome-stable/).

Also, replace ```MAIL_USERNAME``` and ```MAIL_PASSWORD``` properties values used by SMTP.

Then just run ```docker-compose --env-file ".env" up -d```.

### Clients

#### Web site 

open web-page/index.html.


#### Extension 

compress chrome-extension into a zip archive and install it to your browser.

## How to add a new store parser

You can easily add a new website parser.

1. Define [WebSiteParser](../main/server/src/main/java/pricecheckerby/parser/parsers/WebSiteParser.java) interface realization.
1. Add domain data into the ```domain``` table. Add catalog links into the ```catalog_url``` table.

## Database scheme

![db schemes](../media/images/db_scheme.png?raw=true)

