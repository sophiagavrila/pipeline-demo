// declare the url which is our detination for the fetch request
const url = `http://localhost:5000/api/users/add`



let btn = document.getElementById('btn');
btn.addEventListener("click", addUser);


// send the POST request with fetch() inside a method

function addUser() {

    // Build a user object from the input fields by querying the DOM
    let ifirstName = document.getElementById('firstName').value
    let ilastName = document.getElementById('lastName').value
    let iusername = document.getElementById('username').value
    let ipassword = document.getElementById('password').value
    let iemail = document.getElementById('email').value

    // ideally we validate all of the values before including them in our request to the server

    // build the user obj literal 
    let user = {
        firstName: ifirstName,
        lastName: ilastName,
        username: iusername,
        password: ipassword,
        email: iemail
    };

    // we need to build our request OPTIONS (set up the method, body, headers of the request)
    const options = {
        method: 'POST',
        body: JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json'
        }
    }

    // this is just another way of doing the above using some Fetch API Interfaces
    const options2 = new Request(url, {
        method: 'POST',
        body: JSON.stringify(user),
        headers: new Headers({
            'Content-Type': 'application/json'
        })
    })


    fetch(url, options)
        .then(res => res.json())
        .then(res => console.log(res))
}
