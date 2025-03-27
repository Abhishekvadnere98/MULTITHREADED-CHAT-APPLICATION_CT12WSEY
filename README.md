# MULTITHREADED-CHAT-APPLICATION_CT12WSEY

Project Title: Multithreaded Client-Server Chat Application
Company Name: CodTech IT Solutions
Internship ID: CT12WSEY
Intern Name: Abhishek Vadnere
1. Introduction
In today’s fast-paced digital world, real-time communication is essential for seamless interaction between users. This project, Multithreaded Client-Server Chat Application, focuses on developing a real-time chat system that enables multiple users to communicate through a centralized server. The application is built using Java Sockets and Multithreading, ensuring efficient handling of multiple client connections simultaneously.

2. Objective
The primary objective of this project is to design and implement a multithreaded chat application that allows multiple clients to communicate with each other via a server. The server should efficiently manage multiple client connections, relay messages, and ensure data integrity while maintaining smooth real-time interaction.

3. Technologies Used
Programming Language: Java

Concepts Used: Java Sockets, Multithreading, Input/Output Streams, Exception Handling

Development Environment: Eclipse/IntelliJ IDEA

JDK Version: Java SE 8 or later

Networking Protocol: TCP/IP

4. Features of the Chat Application
Server-Side Features:
Handles multiple client connections concurrently using multithreading.

Assigns a unique ID to each client for identification.

Receives and broadcasts messages to all connected clients.

Provides real-time chat functionality using TCP sockets.

Gracefully handles client disconnections.

Client-Side Features:
Connects to the server using Java sockets.

Allows users to send and receive messages in real time.

Displays messages from other clients in a chat interface.

Supports multiple clients joining and leaving dynamically.

5. System Architecture
The system follows a Client-Server Architecture, where:

The Server acts as a central communication hub, managing client connections and message broadcasting.

Each Client connects to the server to send and receive messages.

Workflow:
The server starts and listens for incoming client connections.

A client initiates a connection request to the server.

Once connected, a separate thread is assigned to handle each client.

Clients send messages, which the server receives and relays to all connected users.

When a client disconnects, the server removes it from the active list.

6. Implementation Details
6.1. Server Implementation
The server runs on a fixed port and listens for incoming client connections.

A new thread is created for each connected client, ensuring smooth handling of multiple users.

The server maintains a list of active clients and broadcasts messages.

6.2. Client Implementation
Each client establishes a connection to the server using a socket.

Clients send messages via output streams and receive data via input streams.

A separate thread listens for messages from the server to enable real-time updates.
