let users = []

$.ajax({
    url: `http://localhost:8080/Server_war/api/users`,
    type: "GET",
    async: false,
    success: function (response) {
        users = response;
    }
});

let tbody = document.getElementsByTagName("tbody")[0];
for (let index in users) {
    let div1 = document.createElement("div");
    div1.classList.add("d-flex")
    div1.classList.add("align-items-center")

    let img = document.createElement("img");
    img.setAttribute("style", "width: 45px; height: 45px")
    img.setAttribute("src", "images/man1.png")
    img.classList.add("rounded-circle")

    let div2 = document.createElement("div");
    div2.classList.add("ms-3")
    let p = document.createElement("p");
    p.classList.add("fw-bold")
    p.classList.add("mb-1")
    p.innerHTML = users[index]["username"];
    div2.appendChild(p);

    div1.appendChild(img);
    div1.appendChild(div2);

    let td1 = document.createElement("td");
    td1.appendChild(div1);

    let td2 = document.createElement("td");

    let span = document.createElement("span")
    span.classList.add("badge")
    span.classList.add("rounded-pill")
    span.classList.add("d-inline")

    if (users[index]["at_home"]) {
        span.classList.add("bg-success")
        span.innerHTML = "Present"
    } else {
        span.classList.add("bg-danger")
        span.innerHTML = "Away"
    }

    td2.appendChild(span)

    let tr = document.createElement("tr");
    tr.appendChild(td1);
    tr.appendChild(td2);
    tbody.appendChild(tr);
}