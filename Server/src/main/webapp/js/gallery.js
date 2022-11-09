let images = [];

$.ajax({
    url: `http://localhost:8080/Server_war/api/images`,
    type: "GET",
    async: false,
    success: function (response) {
        images = response;
    }
});

let carousel = document.getElementsByClassName("carousel-inner")[0];

for (let index in images) {
    let name = images[index]["name"];
    let encoding = images[index]["encode"];

    let item = document.createElement("div");
    item.classList.add("carousel-item");
    if (index == 0) {
        item.classList.add("active");
    }

    let image = new Image();
    image.src = `data:image/png;base64,${encoding}`;
    image.setAttribute("style", "max-height: 73vh;");
    image.classList.add("d-block");
    image.classList.add("w-100");

    let div = document.createElement("div");
    div.classList.add("carousel-caption");
    div.classList.add("d-none");
    div.classList.add("d-md-block");
    let h5 = document.createElement("h5");
    h5.innerHTML = name;
    div.appendChild(h5);

    item.appendChild(image);
    item.appendChild(div);
    carousel.appendChild(item);
}