var stompClient = null;

window.onload = connect();

function connect() {
    var socket = new SockJS('/hello');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function(greeting){
            showGreeting(JSON.parse(greeting.body).content);
//            var result = '2'
//                document.getElementById("resultInput").value=greeting.body;

        });
    });
}

function sendName() {
    var name = '0';
//    stompClient.send("/app/hello", {}, {});
    stompClient.send("/app/hello", {}, JSON.stringify({ 'name': name }));

}

function showGreeting(message) {
    console.log(message);
    document.getElementById("resultInput").value=message;
}