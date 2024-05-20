import { useState } from "react";

const Contact = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [subject, setSubject] = useState("");
  const [message, setMessage] = useState("");
  const currentUser = JSON.parse(localStorage.getItem("user"));
  const id = currentUser.id;

  const sendMail = async (e) => {
    if (!name || !email || !subject || !message) {
      alert("Please fill all the fields");
      return;
    }
    e.preventDefault();
    const res = await fetch(
      `http://localhost:8080/api/customer/contact/${id}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + currentUser.jwt,
        },

        body: JSON.stringify({
          email,
          message,
          subject,
        }),
      }
    );
    const data = await res.json();
    if (data.message) {
      console.log(data);
      alert("Mail Sent to Admin successfully");
    } else {
      console.log(data);
    }
  };

  return (
    <div className="bg-blue-400 h-[900px]">
      <h1 className="text-4xl pt-10 text-white text-center">Contact Us</h1>
      <div className=" flex justify-center items-center">
        <form className="flex flex-col gap-4 mt-10">
          <input
            type="text"
            className="p-2 border-solid rounded-md w-full"
            placeholder="Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          <input
            type="text"
            className="p-2 border-solid rounded-md w-full"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <input
            type="text"
            className="p-2 border-solid rounded-md w-full"
            placeholder="Subject"
            value={subject}
            onChange={(e) => setSubject(e.target.value)}
          />

          <textarea
            cols={40}
            rows={3}
            className="p-2 border-solid rounded-md w-full"
            placeholder="Message"
            value={message}
            onChange={(e) => setMessage(e.target.value)}
          ></textarea>
          <button className="bg-green-100 p-3 rounded-md" onClick={sendMail}>
            Send mail
          </button>

          {/* <p className="text-red-500 text-lg">{response}</p> */}
        </form>
      </div>
    </div>
  );
};
export default Contact;
