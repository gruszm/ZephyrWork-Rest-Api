package pl.gruszm.ZephyrWork.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.gruszm.ZephyrWork.entities.Avatar;
import pl.gruszm.ZephyrWork.security.JwtUtils;
import pl.gruszm.ZephyrWork.security.UserDetails;
import pl.gruszm.ZephyrWork.services.AvatarService;

@RestController
@RequestMapping("/api/avatars")
public class AvatarController
{
    private JwtUtils jwtUtils;
    private AvatarService avatarService;

    @Autowired
    public AvatarController(JwtUtils jwtUtils, AvatarService avatarService)
    {
        this.jwtUtils = jwtUtils;
        this.avatarService = avatarService;
    }

    @GetMapping("/token")
    public ResponseEntity<byte[]> getAvatarByToken(@RequestHeader("Auth") String jwt)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        Avatar avatar;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        avatar = avatarService.getAvatarByUser(userDetails.getEmail());

        if (avatar == null)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        else
        {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(avatar.getContentType()))
                    .body(avatar.getImage());
        }
    }

    @PostMapping("/update/token")
    public ResponseEntity<byte[]> updateAvatarForUser(@RequestHeader("Auth") String jwt, @RequestParam("image") MultipartFile file)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        Avatar avatar;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        avatar = avatarService.updateAvatarForUser(userDetails.getEmail(), file);

        if (avatar == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else
        {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(avatar.getContentType()))
                    .body(avatar.getImage());
        }
    }

    @DeleteMapping("/delete/token")
    public ResponseEntity<byte[]> deleteAvatarForUser(@RequestHeader("Auth") String jwt)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        Avatar avatar;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        avatar = avatarService.deleteAvatarForUser(userDetails.getEmail());

        if (avatar == null)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        else
        {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(avatar.getContentType()))
                    .body(avatar.getImage());
        }
    }
}
