INSERT INTO levels (min_level, max_level) VALUES (1, 3);

INSERT INTO game (name, priority, level_id) VALUES ('mygame', 10.5, 1);

INSERT INTO country (country, code) VALUES ('United States', 'US');
INSERT INTO country (country, code) VALUES ('Rumania', 'RO');
INSERT INTO country (country, code) VALUES ('Canada', 'CA');

INSERT INTO game_country (game_id, country_id) VALUES (1, 1);
INSERT INTO game_country (game_id, country_id) VALUES (1, 2);
INSERT INTO game_country (game_id, country_id) VALUES (1, 3);

INSERT INTO items (name) VALUES ('item_1');

INSERT INTO game_items (game_id, item_id) VALUES (1, 1);

INSERT INTO campaign (campaign_name, game_id, start_date, end_date, enabled, last_updated)
       VALUES ('mycampaign', 1, '2022-01-25 00:00:00', '2022-02-25 00:00:00', 1, '2021-07-13 11:46:58');

INSERT INTO inventory (cash, coins) VALUES (123, 123);

INSERT INTO inventory_items (inventory_id, item_id) VALUES (1, 1);

INSERT INTO clan (name) VALUES ('Hello world clan');

INSERT INTO player_profile (player_id, credential, created, modified, last_session, total_spent, total_refund,
            total_transactions, last_purchase, player_level, xp, total_playtime, country, language, birthdate, gender,
            inventory_id, clan_id)
            VALUES ('97983be2-98b7-11e7-90cf-082e5f28d836', 'apple_credential', '2021-01-10 13:37:17', '2021-01-23 13:37:17',
            '2021-01-23 13:37:17', 400, 0, 5, '2021-01-22 13:37:17', 3, 1000, 144, 'CA', 'fr', '2000-01-10 13:37:17', 'male', 1, 1);

INSERT INTO devices (model, carrier, firmware, player_profile_id) VALUES ('apple iphone 11', 'vodafone', 123, 1);




