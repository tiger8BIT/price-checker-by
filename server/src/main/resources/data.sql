INSERT INTO domain (name, url) VALUES ('wildberries', 'https://by.wildberries.ru/catalog');
INSERT INTO catalog_url (id, domain_fk, url) VALUES (1, 'wildberries', '/obuv/muzhskaya/kedy-i-krossovki/krossovki');

INSERT INTO domain (name, url) VALUES ('21vek', 'https://www.21vek.by');
INSERT INTO catalog_url (id, domain_fk, url) VALUES (2,'21vek', '/tv');

INSERT INTO domain (name, url) VALUES ('lamoda', 'https://www.lamoda.by');
INSERT INTO catalog_url (id, domain_fk, url) VALUES (3, 'lamoda', '/c/5971/shoes-muzhkrossovki/');

INSERT INTO domain (name, url) VALUES ('oz', 'https://oz.by');
INSERT INTO catalog_url (domain_fk, url, last_page) VALUES ('oz', '/books/bestsellers', 37);

INSERT INTO domain (name, url) VALUES ('amd', 'https://www.amd.by/catalog');
INSERT INTO catalog_url (domain_fk, url, last_page) VALUES ('amd', '/krupnogabaritnaya-bytovaya-texnika/stiralnye-mashiny/', 59);
