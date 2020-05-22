<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Ninja Web App</title>
		<link href="static/css/Style.css" rel="stylesheet" />
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/3.1.0/mustache.min.js"></script>
		<script src="static/js/main.js"></script>
	</head>
	<body>
		<h1>Ninja App</h1>
		<button id="add-button">ADD NINJA</button>
		<div id="add-form-table-wrapper">
			<table id="add-form-table">
				<thead id="add-form-headings">
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Gender</th>
						<th>Birthday</th>
						<th>Ability</th>
						<th>Village</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<form id="add-form">
						
						<td><input id="input-fname" required pattern="[A-Za-z]{2,15}" placeholder="Write your first name here..."></td>
						<td><input id="input-lname" required pattern="[A-Za-z]{2,15}" placeholder="Write your last name here..."></td>
						<td>
							<div id="input-gender">
								<input type="radio" name="gender" value="Male" id="male-id" required>
								<label for="male-id">Male</label><br>
								<input type="radio" name="gender" value="Female" id="female-id">
								<label for="female-id">Female</label><br>
							</div>
						</td>
						<td><input type="date" id="input-birthday" required></td>
						<td>
							<select id="input-dropdown-ability">
								<option value="Chidori">Chidori</option>
								<option value="Rasengan">Rasengan</option>
								<option value="Amaterasu">Amaterasu</option>
								<option value="Genjutsu">Genjutsu</option>
								<option value="Susano">Susano</option>
							</select>
						</td>
						<td>
							<select id="input-dropdown-village">
								<option value="Konoha">Konoha</option>
								<option value="Sand">Sand</option>
								<option value="Stone">Stone</option>
								<option value="Cloud">Cloud</option>
								<option value="Rain">Rain</option>
							</select>
						</td>
						<td>
							<!-- <button id="add-submit-button" class="control-button">Submit</button> -->
							<input id="add-submit-button" class="control-button" type="submit">
						</td>
						
						</form>
					</tr>
				</tbody>
			</table>
		</div>
		<br>
		<table id="ninjas-table">
			<colgroup>
				<col span="6">
				<col span="1" style="width:60px">
			</colgroup>
			<thead id="existing-list">
				<tr>
					<th >First Name</th>
					<th>Last Name</th>
					<th>Gender</th>
					<th>Birthday</th>
					<th>Ability</th>
					<th>Village</th>
				</tr>
			</thead>
			<tbody>
				<template id="template">
					<tr data-ninja-id="{{ id }}">
						<td><span class="no-edit display-fname">{{ firstName }}</span><input class="edit input-fname"></td>
						<td><span class="no-edit display-lname">{{ lastName }}</span><input class="edit input-lname"></td>
						<td>
							<span class="no-edit display-gender">{{ gender }}</span>
							<div class="edit gender">
								<input type="radio" name="gender" value="Male" id="male-{{ id }}">
								<label for="male-{{ id }}">Male</label><br>
								<input type="radio" name="gender" value="Female" id="female-{{ id }}">
								<label for="female-{{ id }}">Female</label><br>
							</div>
						</td>
						<td><span class="no-edit display-birthday">{{ birthday }}</span><input type="date" class="edit input-birthday"></td>
						<td>
							<span class="no-edit display-ability">{{ ability }}</span>
							<select class="edit dropdown-ability">
								<option value="Chidori">Chidori</option>
								<option value="Rasengan">Rasengan</option>
								<option value="Amaterasu">Amaterasu</option>
								<option value="Genjutsu">Genjutsu</option>
								<option value="Susano">Susano</option>
							</select>
						</td>
						<td>
							<span class="no-edit display-village">{{ village }}</span>
							<select class="edit dropdown-village">
								<option value="Konoha">Konoha</option>
								<option value="Sand">Sand</option>
								<option value="Stone">Stone</option>
								<option value="Cloud">Cloud</option>
								<option value="Rain">Rain</option>
							</select>
						</td>
						<td>
							<button class="control-button edit-button no-edit">Edit</button>
							<button class="control-button delete-button no-edit">Delete</button>
							<button class="control-button edit-submit-button edit">Submit</button>
							<button class="control-button cancel-button edit">Cancel</button>
						</td>
					</tr>
				</template>
			</tbody>
		</table>
	</body>
</html>