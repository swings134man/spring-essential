const createRoomForm = document.getElementById("create-room-form");
const roomList = document.getElementById("room-list");

async function createRoom(name, creatorName) {
    const response = await fetch("/api/rooms", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ name, creatorName })
    });

    if (!response.ok) {
        const error = await response.json().catch(() => null);
        throw new Error(error?.message || "채팅방 생성에 실패했습니다.");
    }

    return response.json();
}

async function deleteRoom(roomId, requesterName) {
    const response = await fetch(`/api/rooms/${roomId}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ requesterName })
    });

    if (!response.ok) {
        const error = await response.json().catch(() => null);
        throw new Error(error?.message || "채팅방 삭제에 실패했습니다.");
    }
}

function bindEnterForms() {
    document.querySelectorAll(".enter-room-form").forEach((form) => {
        form.addEventListener("submit", (event) => {
            event.preventDefault();

            const card = form.closest(".room-card");
            const roomId = card.dataset.roomId;
            const username = form.elements.username.value.trim();

            if (!username) {
                window.alert("사용자 이름을 입력하세요.");
                return;
            }

            const params = new URLSearchParams({ username });
            window.location.href = `/rooms/${roomId}?${params.toString()}`;
        });
    });
}

function bindDeleteForms() {
    document.querySelectorAll(".delete-room-form").forEach((form) => {
        form.addEventListener("submit", async (event) => {
            event.preventDefault();

            const card = form.closest(".room-card");
            const roomId = card.dataset.roomId;
            const requesterName = form.elements.requesterName.value.trim();

            if (!requesterName) {
                window.alert("삭제 요청자 이름을 입력하세요.");
                return;
            }

            try {
                await deleteRoom(roomId, requesterName);
                card.remove();

                if (!roomList.querySelector(".room-card")) {
                    roomList.innerHTML = "<div>생성된 채팅방이 없습니다.</div>";
                }
            } catch (error) {
                window.alert(error.message);
            }
        });
    });
}

function appendRoom(room) {
    const emptyState = roomList.querySelector("div:not(.room-card)");
    if (emptyState) {
        emptyState.remove();
    }

    const roomCard = document.createElement("div");
    roomCard.className = "room-card";
    roomCard.dataset.roomId = room.id;
    roomCard.dataset.roomName = room.name;
    roomCard.innerHTML = `
        <strong>${room.name}</strong>
        <div>Room ID: ${room.id}</div>
        <div>개설자: ${room.creatorName}</div>
        <form class="enter-room-form">
            <label>
                사용자 이름
                <input name="username" type="text" maxlength="30" required>
            </label>
            <button type="submit">입장</button>
        </form>
        <form class="delete-room-form">
            <label>
                삭제 요청자 이름
                <input name="requesterName" type="text" maxlength="30" required>
            </label>
            <button type="submit">삭제</button>
        </form>
    `;
    roomList.appendChild(roomCard);
    bindEnterForms();
    bindDeleteForms();
}

createRoomForm?.addEventListener("submit", async (event) => {
    event.preventDefault();
    const roomNameInput = document.getElementById("room-name");
    const creatorNameInput = document.getElementById("creator-name");
    const roomName = roomNameInput.value.trim();
    const creatorName = creatorNameInput.value.trim();

    if (!roomName) {
        window.alert("채팅방 이름을 입력하세요.");
        return;
    }

    if (!creatorName) {
        window.alert("개설자 이름을 입력하세요.");
        return;
    }

    try {
        const room = await createRoom(roomName, creatorName);
        appendRoom(room);
        createRoomForm.reset();
    } catch (error) {
        window.alert(error.message);
    }
});

bindEnterForms();
bindDeleteForms();
