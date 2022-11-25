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
        error: errorCall
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
        error: errorCall
      }
  )
}

function successCall() {
  alert("전송성공");
  location.reload();
}

function errorCall() {
  alert("전송실패");
}
