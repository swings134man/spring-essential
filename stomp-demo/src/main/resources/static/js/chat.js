const { roomId, username } = window.chatRoomConfig;
const messages = document.getElementById("messages");
const messageForm = document.getElementById("message-form");
const messageInput = document.getElementById("message-input");

let stompClient = null;
let roomDeleted = false;
let redirecting = false;

function formatTimestamp(timestamp) {
    return new Date(timestamp).toLocaleString();
}

function renderMessage(message) {
    if (message.type === "ROOM_DELETED") {
        handleRoomDeleted(message);
        return;
    }

    const item = document.createElement("li");
    item.innerHTML = `
        <div><strong>${message.sender}</strong></div>
        <div>${message.content}</div>
        <div class="meta">${message.type} | ${formatTimestamp(message.timestamp)}</div>
    `;
    messages.appendChild(item);
    item.scrollIntoView({ behavior: "smooth", block: "end" });
}

function handleRoomDeleted(message) {
    roomDeleted = true;
    redirecting = true;
    window.alert(message.content || "채팅방이 삭제되었습니다.");
    stompClient?.deactivate();
    window.location.href = "/";
}

function sendMessage(type, content) {
    if (!stompClient || !stompClient.connected) {
        return;
    }

    stompClient.publish({
        destination: `/app/chat.send/${roomId}`,
        body: JSON.stringify({
            sender: username,
            content,
            type
        })
    });
}

function connect() {
    const socket = new SockJS("/ws");
    stompClient = new StompJs.Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000,
        onConnect: () => {
            stompClient.subscribe(`/topic/rooms/${roomId}`, (frame) => {
                renderMessage(JSON.parse(frame.body));
            });
            sendMessage("ENTER", "");
        }
    });

    stompClient.activate();
}

messageForm?.addEventListener("submit", (event) => {
    event.preventDefault();

    const content = messageInput.value.trim();
    if (!content) {
        return;
    }

    sendMessage("TALK", content);
    messageInput.value = "";
    messageInput.focus();
});

window.addEventListener("beforeunload", () => {
    if (!roomDeleted && !redirecting) {
        sendMessage("LEAVE", "");
    }
    stompClient?.deactivate();
});

connect();
