const toast = document.getElementById("toast");

function showToast(type, msg) {
  toast.style.display = "block";
  toast.className = `toast ${type}`;
  toast.textContent = msg;
}

document.getElementById("btnFill").addEventListener("click", () => {
  document.getElementById("username").value = "admin";
  document.getElementById("password").value = "admin123";
});

document.getElementById("btnLogin").addEventListener("click", async () => {
  const username = document.getElementById("username").value.trim();
  const password = document.getElementById("password").value;

  if (!username || !password) {
    showToast("error", "Please enter username and password.");
    return;
  }

  try {
    const res = await apiFetch("/auth/login", {
      method: "POST",
      body: JSON.stringify({ username, password })
    });

    sessionStorage.setItem("loggedIn", "true");
    sessionStorage.setItem("token", res.token);
    sessionStorage.setItem("role", res.role || "");
    showToast("success", "Login successful. Redirecting...");

    setTimeout(() => window.location.href = "reservation.html", 600);
  } catch (e) {
    showToast("error", e.message);
  }
});
