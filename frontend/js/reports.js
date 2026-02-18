requireAuth();
setActiveNav("reports");

const toast = document.getElementById("toast");
const out = document.getElementById("out");

function showToast(type, msg) {
  toast.style.display = "block";
  toast.className = `toast ${type}`;
  toast.textContent = msg;
}

function renderTable(items) {
  if (!items || items.length === 0) {
    out.innerHTML = `<div class="toast">No records found.</div>`;
    return;
  }
  const rows = items.map(x => `
    <tr>
      <td>${x.reservationNumber}</td>
      <td>${x.guestName}</td>
      <td>${x.contactNumber}</td>
      <td>${x.roomType}</td>
      <td>${x.checkInDate}</td>
      <td>${x.checkOutDate}</td>
    </tr>
  `).join("");

  out.innerHTML = `
    <table class="table">
      <thead>
        <tr>
          <th>Res No</th><th>Guest</th><th>Contact</th><th>Room</th><th>Check In</th><th>Check Out</th>
        </tr>
      </thead>
      <tbody>${rows}</tbody>
    </table>
  `;
}

function renderRevenue(r) {
  out.innerHTML = `
    <div class="kv">
      <div class="k">From</div><div class="v">${r.fromDate}</div>
      <div class="k">To</div><div class="v">${r.toDate}</div>
      <div class="k">Total Reservations</div><div class="v">${r.totalReservations}</div>
      <div class="k">Total Revenue</div><div class="v">${r.totalRevenue}</div>
    </div>
  `;
}

document.getElementById("btnDaily").addEventListener("click", async () => {
  const date = document.getElementById("dailyDate").value;
  if (!date) return showToast("error", "Please select a date.");
  try {
    const items = await apiFetch(`/reports/daily?date=${encodeURIComponent(date)}`);
    showToast("success", "Daily report loaded.");
    renderTable(items);
  } catch (e) {
    showToast("error", e.message);
  }
});

document.getElementById("btnRevenue").addEventListener("click", async () => {
  const from = document.getElementById("fromDate").value;
  const to = document.getElementById("toDate").value;
  if (!from || !to) return showToast("error", "Please select both from and to dates.");
  try {
    const r = await apiFetch(`/reports/revenue?from=${encodeURIComponent(from)}&to=${encodeURIComponent(to)}`);
    showToast("success", "Revenue report loaded.");
    renderRevenue(r);
  } catch (e) {
    showToast("error", e.message);
  }
});
