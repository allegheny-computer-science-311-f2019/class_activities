import gym

# Use the cartpole environment.
env = gym.make('CartPole-v0')
# environment has to be reset first.
env.reset()
# This flag to is used to represent the end of an episode.
# An episode ends when the pole falls down.
done = False
# counter to check the number of moves for which the balance of the pole
# is maintained before it falls down.
t = 0

# run till end of the episode.
while not done:
    # The episode animation will be displayed.
    env.render()
    # choose a random action from the set of available actions.
    action = env.action_space.sample()
    # take the action.
    # The new state and the corresponding reward returned.
    # done flag will be set to true when the episode ends.
    state, reward, done, _ = env.step(action)
    t = t + 1
    print("Action taken : ", action)
    print("Reward obtained: ", reward)
    print("Balanced the pole for this many moves: ", t)
