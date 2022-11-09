$.ajax({
    url: `http://localhost:8080/Server_war/api/account`,
    type: "GET",
    async: false,
    success: function (response) {
        document.getElementById("username").innerHTML = response["username"]
        document.getElementById("full-name").innerHTML = response["full_name"]
    }
});