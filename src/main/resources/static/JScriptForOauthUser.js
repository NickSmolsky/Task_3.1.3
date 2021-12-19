function userInfo() {
    fetch("http://localhost:8080/api/oauthUser")
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
            document.getElementById("oauthUserInfo").innerHTML = temp;
        });
}
userInfo();