function check(n) {
  if (n.checked) {
    add(n.id)
  } else {
    clear(n.id)
  }
}

function delete_extension(n) {
  clear(n.id);
}

function submit() {
  add($('input[name=name]').val());
}

function add(name) {
  let data = {
    "name": name
  }

  $.ajax(
      {
        url: "/extensions",
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: successCall,
        error: errorCall()
      }
  )
}


function clear(name) {
  console.log(name)
  let data = {
    "name": name
  }

  $.ajax(
      {
        url: "/extensions",
        type: "DELETE",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: successCall,
        error: errorCall()
      }
  )
}

function successCall() {
  location.reload();
}


function errorCall() {
  return request => {
    alert("전송실패 : " + request.responseText);
  };
}
