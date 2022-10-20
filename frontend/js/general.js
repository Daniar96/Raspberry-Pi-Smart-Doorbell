function load_page(page) {
    $(function () {
        let includes = $('[data-include]')
        $.each(includes, function () {
            let file = $(this).data('include')
            $(this).load(file)
        })
    })

    $(window).on('load', function() {
        let btn = document.getElementById(`${page}-btn`);
        btn.classList.add("btn-success");
        btn.classList.remove("btn-light");
    });
}