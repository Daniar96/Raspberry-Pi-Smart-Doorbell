function onOff() {
    const status = document.getElementById("alarm_status");
    if (status.textContent === 'Off') {
        status.textContent = 'On';
        $.ajax({
            url: `http://localhost:8080/Server_war/api/RPI/startPIR`,
            type: "POST",
            async: false,
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
            async: false,
            contentType: "application/json",
            success: function () {
                console.log("alarm deactivated")
            }
        });
    }
}