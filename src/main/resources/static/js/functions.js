function sortTable(n) {
  let table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("myTable");
  switching = true;
  // Set the sorting direction to ascending:
  dir = "asc";
  /* Make a loop that will continue until
  no switching has been done: */
  while (switching) {
    // Start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /* Loop through all table rows (except the
    first, which contains table headers): */
    for (i = 1; i < (rows.length - 1); i++) {
      // Start by saying there should be no switching:
      shouldSwitch = false;
      /* Get the two elements you want to compare,
      one from current row and one from the next: */
      x = rows[i].getElementsByTagName("TD")[n];
      y = rows[i + 1].getElementsByTagName("TD")[n];
      /* Check if the two rows should switch place,
      based on the direction, asc or desc: */
      if (dir == "asc") {
        if (isNaN(x.innerHTML)){
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
        }else {
          if (Number(x.innerHTML) > Number(y.innerHTML)) {
            // If so, mark as a switch and break the loop:
            shouldSwitch = true;
            break;
          }
        }
      } else if (dir == "desc") {
        if (isNaN(x.innerHTML)){
          if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
            // If so, mark as a switch and break the loop:
            shouldSwitch = true;
            break;
          }
        } else {
          if (Number(x.innerHTML) < Number(y.innerHTML)) {
            // If so, mark as a switch and break the loop:
            shouldSwitch = true;
            break;
          }
        }

      }
    }
    if (shouldSwitch) {
      /* If a switch has been marked, make the switch
      and mark that a switch has been done: */
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      // Each time a switch is done, increase this count by 1:
      switchcount++;
    } else {
      /* If no switching has been done AND the direction is "asc",
      set the direction to "desc" and run the while loop again. */
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}

function convertDate(d) {
  let p = d.split("/");
  return +(p[2] + p[1] + p[0]);
}

function sortByDate(index) {
  let el = document.getElementsByTagName('TH')[index];
  let o = el.classList.contains('asc') ? 'desc' : 'asc';
  el.classList.remove('asc', 'desc');
  el.classList.add(o);

  let tbody = document.querySelector("#myTable tbody");
  // get trs as array for ease of use
  let rows = [].slice.call(tbody.querySelectorAll("tr"));

  if (o == 'asc') {
    rows.sort(function (a, b) {
      return convertDate(a.cells[index].innerHTML) - convertDate(b.cells[index].innerHTML);
    });
  } else {
    rows.sort(function (a, b) {
      return convertDate(b.cells[index].innerHTML) - convertDate(a.cells[index].innerHTML);
    });
  }



  rows.forEach(function (v) {
    tbody.appendChild(v); // note that .appendChild() *moves* elements
  });
}

function convertTime(d) {
  let f = d.split(" to ");
  let p = f[0].split(":");
  return +(p[0] + p[1]);
}

function sortByTime(index) {
  let el = document.getElementsByTagName('TH')[index];
  let o = el.classList.contains('asc') ? 'desc' : 'asc';
  el.classList.remove('asc', 'desc');
  el.classList.add(o);

  let tbody = document.querySelector("#myTable tbody");
  // get trs as array for ease of use
  let rows = [].slice.call(tbody.querySelectorAll("tr"));

  if (o == 'asc') {
    rows.sort(function (a, b) {
      return convertTime(a.cells[index].innerHTML) - convertTime(b.cells[index].innerHTML);
    });
  } else {
    rows.sort(function (a, b) {
      return convertTime(b.cells[index].innerHTML) - convertTime(a.cells[index].innerHTML);
    });
  }


  rows.forEach(function (v) {
    tbody.appendChild(v); // note that .appendChild() *moves* elements
  });
}

function searchTable() {
  let input, filter, found, table, tr, td, i, j;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("myTable");
  tr = table.getElementsByTagName("tr");
  for (i = 1; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td");
    for (j = 0; j < td.length; j++) {
      if (td[j].innerHTML.toUpperCase().indexOf(filter) > -1) {
        found = true;
      }
    }
    if (found) {
      tr[i].style.display = "";
      found = false;
    } else {
      tr[i].style.display = "none";
    }
  }
}

function sortByText(index) {
  let el = document.getElementsByTagName('TH')[index];
  let o = el.classList.contains('asc') ? 'desc' : 'asc';
  el.classList.remove('asc', 'desc');
  el.classList.add(o);

  let tbody = document.querySelector("#myTable tbody");
  // get trs as array for ease of use
  let rows = [].slice.call(tbody.querySelectorAll("tr"));

  if (o == 'asc') {
    rows.sort(function (a, b) {
      return a.cells[index].innerHTML.localeCompare(b.cells[index].innerHTML);
    });
  } else {
    rows.sort(function (a, b) {
      return b.cells[index].innerHTML.localeCompare(a.cells[index].innerHTML);
    });
  }


  rows.forEach(function (v) {
    tbody.appendChild(v); // note that .appendChild() *moves* elements
  });
}

function sortById(index) {
  let el = document.getElementsByTagName('TH')[index];
  let o = el.classList.contains('asc') ? 'desc' : 'asc';
  Array.from(document.getElementsByTagName('TH')).forEach(th => {
    th.classList.remove('asc', 'desc');
  });
  el.classList.add(o);

  let tbody = document.querySelector("#myTable tbody");
  // get trs as array for ease of use
  let rows = [].slice.call(tbody.querySelectorAll("tr"));

  if (o == 'asc') {
    rows.sort(function (a, b) {
      return Number(a.cells[index].innerHTML) - Number(b.cells[index].innerHTML);
    });
  } else {
    rows.sort(function (a, b) {
      return Number(b.cells[index].innerHTML) - Number(a.cells[index].innerHTML);
    });
  }


  rows.forEach(function (v) {
    tbody.appendChild(v); // note that .appendChild() *moves* elements
  });
}


async function downloadTableWithPdf() {

  let title = document.title;
  let divElements = document.getElementById('wholeTable').innerHTML;
  let printWindow = window.open("", "_blank", "");
  //open the window
  await printWindow.document.open();
  //write the html to the new window, link to css file
  await printWindow.document.write('<html><head><title>' + title + '</title><script src="/js/bootstrap.bundle.min.js"></script><link href="/css/bootstrap.min.css" rel="stylesheet"><link href="/css/style.css" rel="stylesheet"></head><body>');
  await printWindow.document.write(divElements);
  await printWindow.document.write('</body></html>');
  await printWindow.document.close();
  await printWindow.focus();
  //The Timeout is ONLY to make Safari work, but it still works with FF, IE & Chrome.
  await setTimeout(function () {
    printWindow.print();
    printWindow.close();
  }, 100);
}

function enableEmployeeDetails(){
  let job = document.getElementById('jobComplete');
  let employer = document.getElementById('employerComplete');
  job.readOnly = false;
  employer.readOnly = false;
  job.required = true;
  employer.required = true;
}

function disableEmployeeDetails(){
  let job = document.getElementById('jobComplete');
  let employer = document.getElementById('employerComplete');
  job.readOnly = true;
  employer.readOnly = true;
  job.value = null;
  employer.value = null;
  job.required = false;
  employer.required = false;
}

function ConfirmSelection()
{
  let r = confirm("Are you sure you wish to select this GP?");
  if (r !== true) {
    event.preventDefault();
  }
}

function ConfirmSpecialistSelection()
{
  let r = confirm("Are you sure you wish to select this specialist?");
  if (r !== true) {
    event.preventDefault();
  }
}

function fireIvo(){
  document.getElementById("dialogWindow").showModal();
}


