// change alarm status
function onOff() {
    const status = document.getElementById("alarm_status");
    if (status.textContent === 'Off') {
        status.textContent = 'On';
        $.ajax({
            url: `http://localhost:8080/Server_war/api/RPI/startPIR`,
            type: "POST",
            async: true,
            contentType: "application/json",
            success: function () {
                console.log("alarm activated")
            }
        });
    } else {
        status.textContent = 'Off';
        $.ajax({
            url: `http://localhost:8080/Server_war/api/RPI/closePIR`,
            type: "POST",
            async: true,
            contentType: "application/json",
            success: function () {
                console.log("alarm deactivated")
            }
        });
    }
}

function update_dashboard() {
    // get alarm status
    $.ajax({
        url: `http://localhost:8080/Server_war/api/RPI/getPIR`,
        type: "GET",
        async: false,
        success: function (number) {
            if (number == 0) {
                document.getElementById("alarm_status").innerHTML = "Off"
                document.getElementById("customSwitches").checked = false;
            } else {
                document.getElementById("alarm_status").innerHTML = "On"
                document.getElementById("customSwitches").checked = true;
            }
        }
    });

    // smoke sensor
    $.ajax({
        url: `http://localhost:8080/Server_war/api/RPI/smokeAlert`,
        type: "GET",
        async: false,
        success: function (number) {
            if (number === 0) {
                document.getElementById("smoke-detected").innerHTML = "No"
            } else {
                document.getElementById("smoke-detected").innerHTML = "Yes"
            }
        }
    });

    // flame sensor
    $.ajax({
        url: `http://localhost:8080/Server_war/api/RPI/flameAlert`,
        type: "GET",
        async: false,
        success: function (number) {
            if (number === 0) {
                document.getElementById("flame-detected").innerHTML = "No"
            } else {
                document.getElementById("flame-detected").innerHTML = "Yes"
            }
        }
    });

    // temperature sensor
    $.ajax({
        url: `http://localhost:8080/Server_war/api/RPI/temp`,
        type: "GET",
        async: false,
        success: function (response) {
            document.getElementById("temp").innerHTML = `${response["temp"]} °C`
        }
    });

    // notifications table (3 most recent)
    $.ajax({
        url: `http://localhost:8080/Server_war/api/logs`,
        type: "GET",
        async: false,
        success: function (response) {
            let tbody = document.getElementsByTagName("tbody")[0];
            tbody.innerHTML = "";
            for (let index in ["0", "1", "2"]) {
                let td1 = document.createElement("td");
                td1.innerHTML = response[index]["log"];

                let td2 = document.createElement("td");
                td2.innerHTML = response[index]["date"]

                let tr = document.createElement("tr");
                tr.appendChild(td1);
                tr.appendChild(td2);
                tbody.appendChild(tr);
            }
        }
    });

    setTimeout(update_dashboard, 1000);
}

function update_image() {
    // upload last image taken
    $.ajax({
        url: `http://localhost:8080/Server_war/api/images`,
        type: "GET",
        async: false,
        success: function (response) {
            let encoding = response[0]["encode"];
            let image = new Image();
            image.src = `data:image/png;base64,${encoding}`;
            image.setAttribute("style", "max-height: 47vh;");
            image.classList.add("d-block");
            image.classList.add("w-100");

            let row = document.getElementById("last-image");
            row.innerHTML = "";
            row.appendChild(image);
        }
    });

    setTimeout(update_image, 10000);
}

update_dashboard();
update_image();