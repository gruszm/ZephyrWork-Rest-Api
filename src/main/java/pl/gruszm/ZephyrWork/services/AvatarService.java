package pl.gruszm.ZephyrWork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.gruszm.ZephyrWork.entities.Avatar;
import pl.gruszm.ZephyrWork.entities.User;
import pl.gruszm.ZephyrWork.repostitories.AvatarRepository;
import pl.gruszm.ZephyrWork.repostitories.UserRepository;

import java.io.IOException;

@Service
public class AvatarService
{
    private AvatarRepository avatarRepository;
    private UserRepository userRepository;

    @Autowired
    public AvatarService(AvatarRepository avatarRepository, UserRepository userRepository)
    {
        this.avatarRepository = avatarRepository;
        this.userRepository = userRepository;
    }

    public Avatar updateAvatarForUser(String email, MultipartFile file)
    {
        User user = userRepository.findByEmail(email);

        if (user == null)
        {
            return null;
        }

        // Check, if the file is an image
        String fileContentType = file.getContentType();

        if ((fileContentType.equals(MediaType.IMAGE_PNG_VALUE)) || (fileContentType.equals(MediaType.IMAGE_JPEG_VALUE)))
        {
            try
            {
                Avatar avatar = user.getAvatar();

                if (avatar == null)
                {
                    avatar = new Avatar(user, file.getBytes(), fileContentType);
                    user.setAvatar(avatar);
                }
                else
                {
                    avatar.setImage(file.getBytes());
                    avatar.setContentType(fileContentType);
                }

                Avatar savedAvatar = avatarRepository.save(avatar);

                return savedAvatar;
            }
            catch (IOException e)
            {
                return null;
            }
        }

        return null;
    }

    public Avatar getAvatarByUser(String email)
    {
        User user = userRepository.findByEmail(email);

        if (user == null)
        {
            return null;
        }

        return user.getAvatar();
    }

    public Avatar deleteAvatarForUser(String email)
    {
        User user = userRepository.findByEmail(email);
        Avatar avatar;

        if (user == null)
        {
            return null;
        }

        avatar = user.getAvatar();

        if (avatar == null)
        {
            return null;
        }

        avatarRepository.delete(avatar);

        return avatar;
    }
}
