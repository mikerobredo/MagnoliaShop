<section id="contact">
      <h2>${content.title}</h2>
      <div class="content">
        
        
        
        <form id="form" class="col" action="https://www.freecodecamp.com/email-submit">
          <div class="input-group">
            <label for="name">${content.name}</label>
            <input type="text" name="name" id="name">
          </div>
          <div class="input-group">
            <label for="email">${content.email}</label>
            <input type="email" name="email" id="email" placeholder="name@domain.com" required>
          </div>
          <div class="input-group">
            <label for="msg">${content.mensage}</label>
            <textarea name="msg" rows="5"></textarea>
          </div>
          <input type="submit" id="submit" value="enviar">
        </form>
        
        
        <div class="col" id="starfleet">
          
         <img width="300px" src="${damfn.getAssetLink(content.image)!""}"/>

          
        </div>
        
      </div>
    </section>