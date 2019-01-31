var i = -1;

function serverTick(server, result){
    if(i == server.countofPlayers()){
        return;
    }
    manager.send("count: " + server.countofPlayers() + " - " + result);
    i = server.countofPlayers();
}

function onEnable(plugindesc){
    manager.send(plugindesc.getName() + " - Eu fui carregado!");
    manager.addCommand("testgirl", "gogo");
}

function gogo(server, args){
     manager.send("Comando: gogo")
}

function receivePacketEvent(player, packet, event){
    manager.send("Player: " + player.getUsername());
}