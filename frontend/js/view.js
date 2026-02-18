requireAuth();
setActiveNav("view");

const toast = document.getElementById("toast");
const result = document.getElementById("result");

function showToast(type, msg) {
  toast.style.display = "block";
  toast.className = `toast ${type}`;
  toast.textContent = msg;
}

function showResult(r) {
  document.getElementById("rNumber").textContent = r.reservationNumber;
  document.getElementById("rGuest").textContent = r.guestName;
  document.getElementById("rAddress").textContent = r.address;
  document.getElementById("rContact").textContent = r.contactNumber;
  document.getElementById("rType").textContent = r.roomType;
  document.getElementById("rIn").textContent = r.checkInDate;
  document.getElementById("rOut").textContent = r.checkOutDate;

  sessionStorage.setItem("lastReservationNumber", r.reservationNumber);
  result.style.display = "block";
}

document.getElementById("btnUseLast").addEventListener("click", () => {
  const last = sessionStorage.getItem("lastReservationNumber");
  if (!last) {
    showToast("error", "No last reservation number found yet.");
    return;
  }
  document.getElementById("searchNumber").value = last;
});

document.getElementById("btnSearch").addEventListener("click", async () => {
  const num = document.getElementById("searchNumber").value.trim();
  if (!num) {
    showToast("error", "Please enter a reservation number.");
    return;
  }

  try {
    const r = await apiFetch(`/reservations/by-number/${encodeURIComponent(num)}`);
    showToast("success", "Reservation found.");
    showResult(r);
  } catch (e) {
    result.style.display = "none";
    showToast("error", e.message);
  }
});
