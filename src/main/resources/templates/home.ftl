<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<h1>Hello, ${user.login}!</h1>
<h3>Your roles:</h3>
<ul>
<#list user.roles as r>
    <li>
        <b>${r.name}</b>:
        <ul>
        <#list r.privileges as p>
            <li>${p.name}</li>
        </#list>
        </ul>
    </li>
</#list>
    <h3>Documents in system:</h3>
<#list documents as d>
    <li>
        <b>${d.name}</b>
        <br>
        <a href="/download/${d.id}">Download file</a>
        <br>
        <button data-doc-id="${d.id}" onclick="openEditTextarea(event)">Edit file</button>
        <br>
        <label for="${d.id}" style="display: none"> Edit file: ${d.name}
            <br>
            <textarea  id="${d.id}"></textarea>
            <br>
            <button data-doc-id="${d.id}" onclick="save(event)">Save</button>
        </label>
    </li>
</#list>
</ul>
<script>
    function openEditTextarea(event) {
        var fileId = event.target.dataset.docId;
        var textarea = document.getElementById(fileId);
        textarea.parentElement.style.display = "block";
        $.ajax({
            url: "/fileContent",
            method: "get",
            data: {
                "id": fileId
            },
            success: function (text) {
                textarea.value = text;
            }
        });
    }

    function save(event) {
        var fileId = event.target.dataset.docId;
        var textarea = document.getElementById(fileId);
        textarea.parentElement.style.display = "none";
        $.ajax({
            url: "/fileContent",
            method: "put",
            data: {
                "id": fileId,
                "text": textarea.value
            }
        });
    }
</script>
</body>
</html>