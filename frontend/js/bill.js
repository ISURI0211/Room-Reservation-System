requireAuth();
setActiveNav("bill");

const toast = document.getElementById("toast");
const billBox = document.getElementById("billBox");

function showToast(type, msg) {
  toast.style.display = "block";
  toast.className = `toast ${type}`;
  toast.textContent = msg;
}

document.getElementById("btnUseLast").addEventListener("click", () => {
  const last = sessionStorage.getItem("lastReservationNumber");
  if (!last) return showToast("error", "No last reservation number found.");
  document.getElementById("billNumber").value = last;
});

document.getElementById("btnPrint").addEventListener("click", () => window.print());

function showBill(b) {
  document.getElementById("bNum").textContent = b.reservationNumber;
  document.getElementById("bGuest").textContent = b.guestName;
  document.getElementById("bType").textContent = b.roomType;
  document.getElementById("bIn").textContent = b.checkInDate;
  document.getElementById("bOut").textContent = b.checkOutDate;
  document.getElementById("bNights").textContent = b.nights;
  document.getElementById("bRate").textContent = `${b.ratePerNight}`;
  document.getElementById("bTotal").textContent = `${b.totalAmount}`;
  billBox.style.display = "block";

  sessionStorage.setItem("lastReservationNumber", b.reservationNumber);
}

document.getElementById("btnBill").addEventListener("click", async () => {
  const num = document.getElementById("billNumber").value.trim();
  if (!num) return showToast("error", "Please enter a reservation number.");

  try {
    const b = await apiFetch(`/billing/${encodeURIComponent(num)}`);
    showToast("success", "Bill generated.");
    showBill(b);
  } catch (e) {
    billBox.style.display = "none";
    showToast("error", e.message);
  }
});
