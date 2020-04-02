<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<h1>Hello, ${user.login}!</h1>
<h3>Your role is <b>${user.role.name}</b>:</h3><a href="/logout">Log out</a>
<ul>
    <#list user.role.privileges as p>
        <li>${p.name}</li>
    </#list>
</ul>


<h3>Grant role:</h3>
<div>
    <label for="userId">User:
        <select id="userId" name="userId">
            <#list users as u>
                <option value="${u.id}">${u.login}</option>
            </#list>
        </select>
    </label>
    <br>
    <label for="roleId">Role:
        <select id="roleId" name="roleId">
            <#list roles as r>
                <option value="${r.id}">${r.name}</option>
            </#list>
        </select>
    </label>
    <br>
    <button onclick="grantRole(event)">Grant</button>
</div>
<h3>Documents in system:</h3>
<#list documents as d>
    <li>
        <b>${d.name}</b>
        <br>
        <button data-doc-id="${d.id}" onclick="downloadFile(event)">Download file</button>
        <br>
        <button data-doc-id="${d.id}" onclick="openEditTextarea(event)">Edit file</button>
        <br>
        <label for="${d.id}" style="display: none"> Edit file: ${d.name}
            <br>
            <textarea id="${d.id}"></textarea>
            <br>
            <button data-doc-id="${d.id}" onclick="save(event)">Save</button>
        </label>
    </li>
</#list>
</ul>
<script>
    function openEditTextarea(event) {
        var fileId = event.target.dataset.docId;
        $.ajax({
            url: "/fileContent",
            method: "get",
            data: {
                "id": fileId
            },
            statusCode: {
                200: function (text) {
                    var textarea = document.getElementById(fileId);
                    textarea.parentElement.style.display = "block";
                    textarea.value = text;
                },
                403: function () {
                    alert("You don't have rights for this operation. Contact your administrator.");
                }
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
            },
            statusCode: {
                200: function () {
                    alert("Saved successfully!")
                },
                403: function () {
                    alert("You don't have rights for this operation. Contact your administrator.");
                }
            }
        });
    }

    function downloadFile(event) {
        var fileId = event.target.dataset.docId;
        $.ajax({
            url: "/download/" + fileId,
            method: "get",
            statusCode: {
                403: function () {
                    alert("You don't have rights for this operation. Contact your administrator.");
                },
                200: function () {
                    window.location.href = "/download/" + fileId;
                }
            }
        });
    }

    function grantRole(event) {
        var roleId = document.getElementById("roleId").value;
        var userId = document.getElementById("userId").value;
        $.ajax({
            url: "/grant",
            method: "put",
            data: {
                "userId": userId,
                "roleId": roleId
            },
            statusCode: {
                403: function () {
                    alert("You don't have rights for this operation. Contact your administrator.");
                },
                200: function () {
                    alert("Role granted successfully!");
                }
            }
        });
    }
</script>
</body>
</html>