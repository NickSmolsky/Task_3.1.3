// Заполнение таблицы для всех юзеров
function showAllUsers() {
    fetch("http://localhost:8080/api/users")
        .then(response => response.json())
        .then(user => {
            let temp = "";
            user.forEach(user => {
                temp += `<tr>
                            <td> ${user.id} </td>
                            <td> ${user.username} </td>
                            <td> ${user.lastName} </td>
                            <td> ${user.age} </td>
                            <td> ${user.email} </td>
                            <td> ${user.roles.map(role => role.role.substring(5)).join(", ")} </td>
                            <td>
                        <button type="button" class="btn btn-info"
                                data-toggle="modal"
                                data-target="#modalEdit" onclick="openModal(${user.id})">Edit
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger"
                                data-toggle="modal"
                                data-target="#modalDelete" onclick="openModal(${user.id})">Delete
                        </button>
                        </td>
                        </tr>`;
            });
            document.getElementById("UsersTable").innerHTML = temp;
        });
}

showAllUsers()

// Заполнение модальных окон

function openModal(id) {
    fetch("http://localhost:8080/api/user/" + id, {
        headers: {
            "Accept": "application/json", "Content-type": "application/json; charset = UTF-8"
        }
    }).then(response => {
        response.json().then(user => {
            document.getElementById("Id for editUser").value = user.id;
            document.getElementById("First name for editUser").value = user.username;
            document.getElementById("Last Name for editUser").value = user.lastName;
            document.getElementById("Age for editUser").value = user.age;
            document.getElementById("Email for editUser").value = user.email;

            document.getElementById("Id for deleteUser").value = user.id;
            document.getElementById("First name for deleteUser").value = user.username;
            document.getElementById("Last name for deleteUser").value = user.lastName;
            document.getElementById("Age for deleteUser").value = user.age;
            document.getElementById("Email for deleteUser").value = user.email;
        })
    });
}

let roleListForEditUser = () => {
    let roleArray = []
    let options = document.querySelector("#editRoleForEditUser").options
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            let role = {id: options[i].value, role: options[i].text}
            roleArray.push(role)
        }
    }
    return roleArray;
}

let roleListForNewUser = () => {
    let roleArray = []
    let options = document.querySelector("#addRoleForNewUser").options
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            let role = {id: options[i].value, role: options[i].text}
            roleArray.push(role)
        }
    }
    return roleArray;
}


document.getElementById("editUserForm").addEventListener("submit", editUser);

async function editUser() {
    let user = {
        "username": document.getElementById("First name for editUser").value,
        "password": document.getElementById("Password for editUser").value,
        "roles": roleListForEditUser(),
        "lastName": document.getElementById("Last Name for editUser").value,
        "age": document.getElementById("Age for editUser").value,
        "email": document.getElementById("Email for editUser").value
    }
    await fetch("http://localhost:8080/api/users/" + document.getElementById("Id for editUser").value, {
        method: "PATCH",
        headers: {
            "Accept": "application/json",
            "Content-type": "application/json; charset = UTF-8"
        },
        body: JSON.stringify(user)
    });
    setTimeout(showAllUsers, 500);
    $("#modalEdit .close").click();

}


document.getElementById("newUserForm").addEventListener("submit", newUser);

async function newUser() {
    let user = {
        "username": document.getElementById("First name for newUser").value,
        "password": document.getElementById("Password for newUser").value,
        "roles": roleListForNewUser(),
        "lastName": document.getElementById("Last Name for newUser").value,
        "age": document.getElementById("Age for newUser").value,
        "email": document.getElementById("Email for newUser").value
    }
    await fetch("http://localhost:8080/api/users", {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-type": "application/json; charset = UTF-8"
        },
        body: JSON.stringify(user)
    });
    setTimeout(showAllUsers, 500);
    $("#modalNewUser .close").click();
}


document.getElementById("deleteUserForm").addEventListener("submit", deleteUser);

async function deleteUser() {
    await fetch("http://localhost:8080/api/users/" + document.getElementById("Id for deleteUser").value, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    })

    setTimeout(showAllUsers, 500);
    $("#modalDelete .close").click();
}


function adminInfo() {
    fetch("http://localhost:8080/api/user")
        .then(response => response.json())
        .then(user => {
            let temp = `<tr>
                            <td> ${user.id} </td>
                            <td> ${user.username} </td>
                            <td> ${user.lastName} </td>
                            <td> ${user.age} </td>
                            <td> ${user.email} </td>
                            <td> ${user.roles.map(role => role.role.substring(5)).join(", ")} </td>                      
                            </tr>`
            document.getElementById("adminInfo").innerHTML = temp;
        });
}
adminInfo();

