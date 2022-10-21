function onOff() {
    const status = document.getElementById("alarm_status");
    if (status.textContent === 'Off') {
        status.textContent = 'On';
    } else {
        status.textContent = 'Off';
    }
}