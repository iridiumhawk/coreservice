package com.cherkasov.listeners;

/**
 * Set on rest method. On getting new value from device checks who is subscribe adn sends them push
 */
public class PushListener {
    // TODO: 07.05.2018 have to realise
}

/*Привет
На бэке надо делать логику
-триггеров событий (events) - для boolean сигналов - изменение состояний всегда,
для дискретных - пока не делаем (надо будет уставки добавлять) - подписка на события?
-журналы этих событий - хранить и выдавать по rest api,
-отправку этих events на смс (на любой внешний сервис, слак, телеграм, веб...)*/